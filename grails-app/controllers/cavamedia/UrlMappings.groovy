package cavamedia

class UrlMappings {

    static mappings = {

        "/media/images" {
            controller = "media"
            action = "index"
            type = "image"
        }

        "/media/videos" {
            controller = "media"
            action = "index"
            type = "video"
        }

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
