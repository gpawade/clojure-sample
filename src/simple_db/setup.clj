(ns simple-db.setup
    (:require [clojure.data.csv :as csv]
              [clojure.java.io :as io]
              [clojure.string :as s]
              [cheshire.core :as json]
              [clojure.java.jdbc :as jdbc]))

  (defonce album-data-file "albumlist.csv")
  (defonce album-data-json-file "albumlist.json")
  (defonce loaded? (atom false))
  (defonce album-data [])

  (defn create-albums-data-json-file [album-data out-file]
    (if-not (.exists (java.io.File. out-file))
      (spit out-file
            (json/generate-string album-data))
      :no-op))

  (defn load-albums-csv-file [album-data-file]
    (let [data (-> album-data-file
                   io/resource
                   io/as-file
                   slurp)
          csv (csv/read-csv data)
          header (first csv)
          header (map s/lower-case header)
          data (rest csv)
          processed (map zipmap
                         (->> header
                              (map keyword)
                              repeat)
                         data)]
      processed))

  (create-albums-data-json-file album-data album-data-json-file)

  (def hsqldb {:dbtype "hsqldb"
               :dbname "albums"})

  (def albums-table-create-ddl
    (jdbc/create-table-ddl
     :albums
     [[:number :int]
      [:year :int]
      [:album "varchar(128)"]
      [:artist "varchar(128)"]
      [:genre "varchar(128)"]
      [:subgenre "varchar(128)"]]
     {:conditional? true}))

(def albums-table-drop-ddl
  (jdbc/drop-table-ddl :albums))


  (defn init-db! [db-spec]
    (let []
      (jdbc/db-do-commands
       db-spec
       [ ;albums-table-drop-ddl
        albums-table-create-ddl
        "CREATE INDEX year_ix ON albums (year);"])
      (jdbc/insert-multi! db-spec
                          :albums
                        album-data)))

  (when-not @loaded?
    (let [processed (load-albums-csv-file album-data-file)]
      (alter-var-root #'album-data (constantly processed))
      (create-albums-data-json-file processed album-data-json-file)
      (init-db! hsqldb)
      (reset! loaded? true)))
