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

(defn getchecklistdetail [data id]
  (map-indexed (fn [idx itm] (conj {} {
                                        :title (:text itm)
                                        :_id (str id "_3_" idx)
                                        :id id
                                        :value (:checked itm)
                                        })) data)
  )
(defn getchecklistbyid [id]
  (let [
         idarr (clojure.string/split id #"_")
         ;level (second   idarr)
         data (:checklist ( :content (db/get-tempdetail-byid (ObjectId. (first idarr)))))

         content (map-indexed (fn [idx itm] (do (conj {} {
                                                       :title (:text itm)
                                                       :state "opened"
                                                       :_id (str (first idarr) "_2_" idx)
                                                       :id (first idarr)
                                                       :children (getchecklistdetail (:data itm) (first idarr))
                                                       }))) data)

         ]
    content
    )

  )
(defn gettemptree [id]
    (let [
        temps (if (nil? id) [{:state "opened" :title "全部" :_id "0" :children (map #(conj % {:state "opened" :children [
                                                                                                                        {:_id (str (:_id %) "_1_0") :id (:_id %) :title "自定义处置内容" :value (:options ( :content (db/get-tempdetail-byid (:_id %))))  :prop "options" :state "opened" }
                                                                                                                        {:_id (str (:_id %) "_1_1") :id (:_id %) :title "模板套餐列表" :prop "checklist" :state "closed" }
                                                                                                                        ]}) (db/get-temps))}]

                (getchecklistbyid id))
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

;;新增模板
(defn addtemp0[title]

    (try
      (let [
             item {:title title :content {:checklist [] :options ""}}
             ]

      (do
        (ok {:success true :message "添加成功" :id  (:_id (db/addtemp item))})
        )
      )
      (catch Exception ex
        (ok {:success false :message (.getMessage ex)})
        )

    )

  )

(defn altermm10 [id value]

  (try
    (let [
           olddata ( :content (db/get-tempdetail-byid (ObjectId. id)))
           item {:content {:checklist (:checklist olddata) :options value}}
           ]

      (do
        (db/updatetempbyid item (ObjectId. id))
        (ok {:success true :message "添加成功" })
        )
      )
    (catch Exception ex
      (ok {:success false :message (.getMessage ex)})
      )

    )

  )
  ;;新增模板
(defn addtemp1[text id]

    (try
      (let [
             item {:text text :data []}
             olddata ( :content (db/get-tempdetail-byid (ObjectId. id)))
             data (:checklist  olddata)
             newitem (conj data item)
             ]

      (do
        (db/updatetempbyid {:content {:checklist newitem :options (:options  olddata)}} (ObjectId. id))
        (ok {:success true :message "添加成功"})
        )
      )
      (catch Exception ex
        (ok {:success false :message (.getMessage ex)})
        )

    )

  )

;;新增模板
(defn addtemp2[keytext text id]

    (try
      (let [

             olddata ( :content (db/get-tempdetail-byid (ObjectId. id)))
             data (:checklist  olddata)
             childrendata (first (filter (fn [x]
                                    (= (:text x) keytext)) data))
             childrendatano  (filter (fn [x]
                                           (not= (:text x) keytext)) data)
             newchildrendata (conj (:data childrendata) {:text text :checked false} )

             newitem (conj childrendatano {:text keytext :data newchildrendata})
             ]

      (do
        (db/updatetempbyid {:content {:checklist newitem :options (:options  olddata)}} (ObjectId. id))
        (ok {:success true :message "添加成功"})
        )
      )
      (catch Exception ex
        (ok {:success false :message (.getMessage ex)})
        )

    )

  )




(defn removetemp2[text id]

    (try
      (let [
             item {:text text :data []}
             olddata ( :content (db/get-tempdetail-byid (ObjectId. id)))
             data (:checklist  olddata)
             newitem (filter (fn [x]
                               (not= (:text x) text)) data)
             ]

      (do
        (db/updatetempbyid {:content {:checklist newitem :options (:options  olddata)}} (ObjectId. id))
        (ok {:success true :message "删除成功"})
        )
      )
      (catch Exception ex
        (ok {:success false :message (.getMessage ex)})
        )

    )

  )

(defn removetemp3[parenttext text id]

  (try
    (let [

           olddata ( :content (db/get-tempdetail-byid (ObjectId. id)))
           data (:checklist  olddata)
           childrendata (first (filter (fn [x]
                                         (= (:text x) parenttext)) data))
           childrendatano  (filter (fn [x]
                                     (not= (:text x) parenttext)) data)
           newchildrendata (filter (fn [x](not= (:text x) text) ) (:data childrendata) )

           newitem (conj childrendatano {:text parenttext :data newchildrendata})
           ]

      (do
        (db/updatetempbyid {:content {:checklist newitem :options (:options  olddata)}} (ObjectId. id))
        (ok {:success true :message "添加成功"})
        )
      )
    (catch Exception ex
      (ok {:success false :message (.getMessage ex)})
      )

    )

  )

(defn removemm01 [id]
  (try
      (do
        (db/removetempbyid (ObjectId. id))
        (ok {:success true :message "删除成功" })
        )

    (catch Exception ex
      (ok {:success false :message (.getMessage ex)})
      )

    )
  )

(defn altertemp3[parenttext text id value]

  (try
    (let [

           olddata ( :content (db/get-tempdetail-byid (ObjectId. id)))
           data (:checklist  olddata)
           childrendata (first (filter (fn [x]
                                         (= (:text x) parenttext)) data))
           childrendatano  (filter (fn [x]
                                     (not= (:text x) parenttext)) data)
           newchildrendata (conj (filter (fn [x](not= (:text x) text) ) (:data childrendata) ) {:text text :checked value})

           newitem (conj childrendatano {:text parenttext :data newchildrendata})
           ]

      (do
        (db/updatetempbyid {:content {:checklist newitem :options (:options  olddata)}} (ObjectId. id))
        (ok {:success true :message "添加成功"})
        )
      )
    (catch Exception ex
      (ok {:success false :message (.getMessage ex)})
      )

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

