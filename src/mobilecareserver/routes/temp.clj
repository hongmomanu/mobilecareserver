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
  (POST "/temp/saverecord" [id content] (temp/saverecord id content))
 )

