(ns mobilecareserver.controller.temp
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





(defn gettemp []
    (let [
        temps (db/get-temps)
    ]

    (ok temps)
    )
)
(defn getrecord [page limit]
    (let [
           page (read-string page)
           limit (read-string limit)
            records (db/get-record page limit)
    ]

    (ok records)
    )
)
(defn gettempdetailbyid [id]
    (let [
        tempdtetail (db/get-tempdetail-byid (ObjectId. id))
    ]

    (ok tempdtetail)
    )
)
