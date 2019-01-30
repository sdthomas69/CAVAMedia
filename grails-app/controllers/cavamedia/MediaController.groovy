package cavamedia

import groovy.json.JsonBuilder
import org.apache.commons.lang.StringEscapeUtils

class MediaController {

    def map() {}

    def test() {
        Post post = Post.get(393)
        println "metas are ${post.geoMetas}"
        render "done"
    }


    /**
     *
     */
    def index() {

        String imageType = "image/png"

        String videoType = "video/quicktime"

        String type = ""

        String query = "select distinct post from Post post , Meta meta where post.id=meta.post and post.status='inherit' "

        if (params.type) {

            if (params?.type == "image") type = imageType

            if (params?.type == "video") type = videoType

            query += "and post.mimeType='${type}'"

        } else {
            query += "and (post.mimeType='${imageType}' or post.mimeType='${videoType}') and post.status='inherit'"
        }

        List posts = Post.executeQuery(query)

        response.setContentType("text/json")

        render stringBuildJson(posts)
    }

    /**
     *
     * @param posts
     * @return
     */

    private stringBuildJson(List posts) {

        def json = StringBuilder.newInstance()

        json << '{ "type": "FeatureCollection", "features": ['

        posts.eachWithIndex { post, index ->

            json << buildJson(post)

            if(index + 1 < posts.size()) json << ","
        }
        json << "]}"

        return json.toString()
    }


    /**
     * 
     * @param post
     * @return
     */
    private buildJson(Post post) {

        boolean isVideo = post.mimeType == "video/quicktime"

        def jb = new JsonBuilder()

        Map feature = jb {
            type "Feature"
            geometry {
                type "Point"
                if (post.latitude && post.longitude) {
                    coordinates([post.longitude.metaValue, post.latitude.metaValue])
                }
            }
            properties {
                title post.title
                type post.mimeType
                url post.guid
                //excerpt StringEscapeUtils.escapeHtml(post.excerpt)
                excerpt post.excerpt
                if (isVideo) {
                    if (post.videoURL) {
                        videoURL post.videoURL.metaValue
                    }
                    if (post.videoPoster) {
                        videoPoster post.videoPoster.metaValue
                    }
                }
            }
        }

        jb.toString()
    }
}
