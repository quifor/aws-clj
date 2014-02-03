(ns getting-data.core
  (:require [incanter.core]
            [incanter.io :refer [read-dataset]]))

(comment
  (:relationship (first (:rows foo)))
   (use 'getting-data.core )
  (-> foo :rows first :relationship)


  (map :Column-name (:rows data))
  (def data (get-data "../data/aws-sample.csv" true))

  (-> 7 (inc ,,)  (inc ,,))


  )


;;(def filename "../data/aws-sample.csv")
(def filename "../data/aws-trim.csv")



(defn get-cost-col
  [data ]
  ;;(map col (:rows data))
  (into [] (map :Cost (:rows data)))

  )

(defn sum-cost
  [col-vec]
  (reduce + (map + col-vec)))


(defn total-cost
  [data]
  (sum-cost (get-cost-col data)))

;;needf iltering function to filter out unused rows.
;;find rows with 'idi' in given col

(defn row-value
  "convert row"
  [row]
  {:cost (:Cost row)
   :product (:ProductName row)
   :id (:ResourceId row)})

(defn get-cost-maps
  "turn data into vector of cost maps"
  [data]
  (into [] (map row-value (:rows data))))


(defn get-ids
  [cost-maps]
  (into [](set (map :product cost-maps))))

(defn get-id-cost-map
  [cost-maps]
  ;;(print cost-maps)
  )

(defn sum-cost-maps
  [cost-maps]
  (reduce + (into [] (map :cost cost-maps))))

(defn get-data
  "gets csv data from directory"
  ([csv-path] (get-data csv-path nil))

  ([csv-path header]
   (let [data (read-dataset csv-path :header header)]
     data)))

(defn filter-fn
  [m ]
   (let [a (:a m)]
     (re-matches #".*.*" a)))
;;stdr


(def my-map-vec [{:a "idi-m" :b "3" :id "test"} {:a "idism" :id "i-" } {:a "idi-m1" } {:a "msi"}{:a "idi" }])

(def filter-tag "idi")

(defn filter-fnc
  "takes a filter tag and a vector of cost maps"
  [ filt m ]
;; (print m)
  (def regex (str ".*" filt ".*"))
  (let [a (:id m)]
    (if a
      (str (re-matches (re-pattern regex) a)))))


(:id (first my-map-vec))

;(map #(filter-fnc filter-tag %) my-map-vec )

(defn filter-all
  [filt m]
  (filter-fnc filt m))

(into [](filter #(filter-fnc filter-tag %)  my-map-vec  ))

(defn group-map
  [my-map-vec]
  (->> my-map-vec (group-by :id)))

(defn sum-grp-map-vec
  [map-vec]
    (into [] (map (fn [[k v ]] [k (apply merge-with + (map #(dissoc % :id :product) v))]) map-vec)))

(def data (read-dataset filename :header true))
(def cost-maps (get-cost-maps data))
(def filter-tag "idi")
(def flt-cost-maps (filter-fnc filter-tag cost-maps) )
(def grp-vec (group-map cost-maps))
(def sum-grp-vec (sum-grp-map-vec grp-vec))

(defn process-data
  "process csv data from directory"
  ([csv-path header]
   (def filter-tag "idi")
   (let [data  (read-dataset csv-path :header header)]
     (let [cost-maps (get-cost-maps data)];;turn into vector of maps containing cost for each item
       (let [flt-cost-maps (filter-fnc filter-tag cost-maps) ]
 )))))

