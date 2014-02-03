(ns fwpd.core
  (:require [clojure.string :as s]))

(def filename "suspects.csv")

(def headers->keywords {"Name" :name
                        "Glitter Index" :glitter-index})


(defn str->int
  "if an arguement is a string convert to integer"
  [str]
  (if (string? str)
    (read-string (re-find #"^-?\d+$" str))
      str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn parse
  "convert a csv into rows of columns"
  [string]
  (map #(s/split % #",")
       (s/split string #"\n")))

(defn mapify
  "return a seq of maps"
  [rows]
  (let [headers (map #(get headers->keywords %) (first rows))
        unmapped-rows (rest rows)]
    (map (fn [unmapped-row]
           (into {}
                 (map (fn [header column]
                        [header ((get conversions header) column)])
                        headers
                        unmapped-row)))
                 unmapped-rows)))


(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

