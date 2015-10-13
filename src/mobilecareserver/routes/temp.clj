(ns mobilecareserver.routes.temp
  (:require [mobilecareserver.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :refer [ok]]
            [clojure.data.json :as json]
            [mobilecareserver.controller.temp :as temp]
            [clojure.java.io :as io]))



(defroutes temp-routes
  (GET "/temp/gettemp" [] (temp/gettemp))
  (GET "/temp/gettemptree" [id] (temp/gettemptree id))
  (GET "/temp/getrecord" [page limit] (temp/getrecord page limit))
  (GET "/temp/getrecordbyid" [id] (temp/getrecordbyid id))
  (GET "/temp/getrecordbykey" [key] (temp/getrecordbykey key))
  (GET "/temp/gettempbyid" [id] (temp/gettempdetailbyid id))
  (POST "/temp/addrecord" [params] (temp/addrecord params))
  (POST "/temp/addtemp0" [title] (temp/addtemp0 title))
  (POST "/temp/addtemp1" [text id] (temp/addtemp1 text id))
  (POST "/temp/addtemp2" [keytext text id] (temp/addtemp2 keytext text id))
  (POST "/temp/removetemp2" [text id] (temp/removetemp2 text id))
  (POST "/temp/removetemp3" [parenttext text id] (temp/removetemp3 parenttext text id))
  (POST "/temp/removemm01" [ id] (temp/removemm01  id))
  (POST "/temp/altertemp3" [parenttext text id value] (temp/altertemp3 parenttext text id value))
  (POST "/temp/saverecord" [id content] (temp/saverecord id content))
 )

