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

(defn adduser [username realname password]

  (try
    (let [
           user (db/get-user-byname username)

           ]
      (if (nil? user )
        (do
            (db/add-user {:username username :realname realname :password password})
            (ok {:success true})
                        )
        (ok {:success false :message "用户已经存在"})

        )
      )
    (catch Exception ex
      (ok {:success false :message (.getMessage ex)})
      )

    )

  )

