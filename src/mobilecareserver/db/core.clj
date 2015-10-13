(ns mobilecareserver.db.core
  (:require
    [clojure.java.jdbc :as jdbc]
    [yesql.core :refer [defqueries]]
    [taoensso.timbre :as timbre]
    [monger.core :as mg]
    [monger.collection :as mc]
    [monger.operators :refer :all]
    [monger.query :refer [with-collection find options paginate sort fields]]
     [environ.core :refer [env]])
  (:import java.sql.BatchUpdateException))


(defonce db (let [uri (get (System/getenv) "MONGOHQ_URL" "mongodb://jack:1313@111.1.76.108/mobileapp")
                  {:keys [conn db]} (mg/connect-via-uri uri)]
              db))



(defn get-user-byname [username]
  (mc/find-one-as-map
    db "users" {:username username}
    )

  )


(defn get-temps []
  (mc/find-maps
    db "caretemplists" {} ["title"]
    )

  )
(defn get-record[page limit]

  (with-collection db "carerecordlists"
    (find {} )
    (fields [:username :cardno :time])
    (paginate :page page :per-page limit))


  )

(defn addrecord [item]
  (mc/insert-and-return db "carerecordlists" item)
  )

(defn addtemp [item]
  (mc/insert-and-return db "caretemplists" item)
  )
(defn removetempbyid [oid]
  (mc/remove-by-id db "caretemplists" oid)
  )
(defn updatetempbyid[item id]
  (mc/update-by-id db "caretemplists" id  {$set item} )
  )

;;根据id查询模板详细内容
(defn get-tempdetail-byid [id]
  (mc/find-map-by-id
    db "caretemplists" id ["content"]
    )

  )
;;根据id查询记录
(defn get-record-byid [id]
  (mc/find-map-by-id
    db "carerecordlists" id
    )

  )
;; 通过关键字查询
(defn get-record-bykey [key]
  (mc/find-maps
    db "carerecordlists"  {$or [{:username {$regex (str ".*" key ".*")}}
                                {:cardno {$regex (str ".*" key ".*")}}]}
    )

  )
;;保存最新记录
(defn saverecord [id content]

  (mc/update-by-id db "carerecordlists" id {$set content} )

  )



