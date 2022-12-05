package com.example.youtubeclone

import java.util.Date

open class Resultado {

    var regionCode:String = ""
    lateinit var pageInfo:PageInfo
    lateinit var items:List<Item>
}
class PageInfo{
    var totalResults:String = ""
    var resultsPerPage:String = ""

}
class Item{
  lateinit var id:Id
  lateinit var snippet:Snippet
}
class Id{
 var kind:String = ""
 var videoId:String = ""
}
class Snippet{
    lateinit var publishedAt:Date
    var channelId:String=""
    var title:String=""
    var description:String=""
    var channelTitle:String=""
    var liveBroadcastContent:String=""
    lateinit var thumbnails: Thumbnails

    class Thumbnails{

        lateinit var default:Default
        lateinit var medium: Medium
        lateinit var high: High



        class Default{
            var url:String=""
            var width:String=""
            var height:String=""
        }
        class Medium{
            var url:String=""
            var width:String=""
            var height:String=""
        }
        class High{
            var url:String=""
            var width:String=""
            var height:String=""
        }
    }
}

