(ns mobilecareserver.controller.user
  (:use compojure.core)
  (:require [mobilecareserver.db.core :as db]
            ;[doctorserver.public.common :as common]
            [ring.util.http-response :refer [ok]]
            [clojure.data.json :as json]
            [monger.json]
            )
  (:import [org.bson.types ObjectId]
           )
  )





(defn userlogin [username password]
    (let [
        user (db/get-user-byname username)

    ]
    (if (and user (= password (:password user)))(ok {:success true :user user})
    (ok {:success false}))
    )
)

