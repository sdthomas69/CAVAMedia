package cavamedia

class Meta {

    String metaKey, metaValue

    Post post

    static constraints = {
        metaKey nullable: true
        metaValue nullable: true
    }

    static mapping = {
        table "wp_postmeta"
        version false
        id column: "meta_id"
        post column: "post_id"
        metaKey column: "meta_key"
        metaValue column: "meta_value"
    }
}
