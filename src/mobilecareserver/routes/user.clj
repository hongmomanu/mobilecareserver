(ns mobilecareserver.routes.user
  (:require [mobilecareserver.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :refer [ok]]
            [clojure.data.json :as json]
            [mobilecareserver.controller.user :as user]
            [clojure.java.io :as io]))



(defroutes user-routes
  (GET "/user/login" [username password] (

                                           user/userlogin username password
                                           ))

  (POST "/user/adduser" [username realname password ] (

                                           user/adduser username realname password
                                           ))
  (GET "/user/logout" [] (ok {:success false})))

