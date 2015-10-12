(ns mobilecareserver.controller.temp
  (:use compojure.core)
  (:require [mobilecareserver.db.core :as db]
            ;[doctorserver.public.common :as common]
            [ring.util.http-response :refer [ok]]
            [clojure.data.json :as json]
            [monger.json]
            )
  (:import [org.bson.types ObjectId]
           [java.util  Date Calendar]
           )

  )

;;获取模板
(defn gettemp []
    (let [
        temps (db/get-temps)
    ]

    (ok temps)
    )
)
;;获取模板
(defn gettemptree [id]
    (let [
        temps (if (nil? id) (map #(conj % {:state "opened" :children [
                                                                       {:_id (str (:_id %) "_1") :title "自定义处置内容" :prop "options" :state "opened" }
                                                                       {:_id (str (:_id %) "_2") :title "模板套餐列表" :prop "checklist" :state "closed" }
                                                                       ]}) (db/get-temps))

                (db/get-tempdetail-byid (ObjectId. id)))
    ]

    (ok temps)
    )
)
;;获取历史记录
(defn getrecord [page limit]
    (let [
           page (read-string page)
           limit (read-string limit)
            records (db/get-record page limit)
    ]

    (ok records)
    )
)
;; 根据id获取模板详细信息
(defn gettempdetailbyid [id]
    (let [
        tempdtetail (db/get-tempdetail-byid (ObjectId. id))
    ]

    (ok tempdtetail)
    )
)
;; 根据id获取记录详细信息
(defn getrecordbyid [id]
    (let [
        record (db/get-record-byid (ObjectId. id))
    ]

    (ok record)
    )
)
;; 根据关键字获取记录
(defn getrecordbykey [key]
    (let [
        record (db/get-record-bykey key)
    ]

    (ok record)
    )
)

;;新增记录
(defn addrecord[item]

    (try
      (let [
             item (conj {:time (new Date)} item)
             ]

      (do
        (ok {:success true :id  (:_id (db/addrecord item))})
        )
      )
      (catch Exception ex
        (ok {:success false :message (.getMessage ex)})
        )

    )

  )
;; 保存历史记录
(defn saverecord [id content]

  (try
      (do
        (db/saverecord (ObjectId. id) content)
        (ok {:success true})
        )

    (catch Exception ex
      (ok {:success false :message (.getMessage ex)})
      )

    )
  )

