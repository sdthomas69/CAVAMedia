package cavamedia

class Post {

    Date date

    String title, excerpt, type, mimeType, guid, status

    static mapping = {
        table "wp_posts"
        version false
        id column: "ID"
        title type: "text", column: "post_title"
        excerpt type: "text", column: "post_excerpt"
        type column: "post_type"
        date column: "post_date"
        mimeType column: "post_mime_type"
        status column: "post_status"
    }

    /**
     *
     * @return
     */
    List<Meta> getGeoMetas() {

        def metas = Meta.withCriteria {
            eq("post", this)
            or {
                eq("metaKey", "latitude")
                eq("metaKey", "longitude")
            }
        }
    }

    /**
     *
     * @return
     */
    Meta getLatitude() {
        Meta.findByMetaKeyAndPost("latitude", this)
    }

    /**
     *
     * @return
     */
    Meta getLongitude() {
        Meta.findByMetaKeyAndPost("longitude", this)
    }

    /**
     *
     * @return
     */
    Meta getVideoURL() {
        Meta.findByMetaKeyAndPost("_jwppp-video-url-1", this)
    }

    /**
     *
     * @return
     */
    Meta getVideoPoster() {
        Meta.findByMetaKeyAndPost("_jwppp-video-image-1", this)
    }

    /**
     *
     * @return
     */
    Meta getAttachedFile() {
        Meta.findByMetaKeyAndPost("_wp_attached_file", this)
    }
}
