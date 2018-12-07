(ns simple-db.core
  (:require [clojure.java.jdbc :as jdbc]
            [cheshire.core :as json]
            [simple-db.setup :refer [album-data]]))

;;;;;
; (count album-data)

; (count
;     (filter
;         (fn [album] (= "1967" (:year album)))
;     album-data))


; (first album-data)

; (:genre (first album-data))


; (map :genre album-data)

; (count
;       (into 
;            #{} 
;           (map :genre album-data)))
;;;VS

; (->> album-data
;       (map :genre)
;       distinct
;       count)

;;;;;;;;;;;;;;;;;;


(def hsqldb {:dbtype "hsqldb"
             :dbname "albums"})

; (jdbc/query hsqldb ["SELECT * from albums limit 2"])

; (->>
;  (jdbc/query hsqldb ["SELECT * from albums"])
;  (map :genre)
;  distinct
;  count)

; ;; execution time
; (time (->>
;  (jdbc/query hsqldb ["SELECT * from albums"])
;  (map :genre)
;  distinct
;  count))

;;(jdbc/query hsqldb ["select * from albums where lower"])

;; Generate JSON file fraom album-data
; (spit "albumlist.json" (json/generate-string album-data))

(defn get-album-by-year [year]
  (filter (fn [album] (= year (:year album)))
          album-data))