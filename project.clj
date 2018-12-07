; project.clj

(def jackson-version "2.9.7")

(defproject zero-one "0.1.0-SNAPSHOT"

  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [
                 ;; Everyone wants this
                 [org.clojure/clojure "1.10.0-RC3"]
                 
                 ;; Web stuff
                 [aleph "0.4.6"]
                 [ring/ring-json "0.4.0"]
                 [compojure "1.6.1"]
                 [cheshire "5.8.1"]
                 [hiccup "1.0.5"]

                 ;; CSV
                 [org.clojure/data.csv "0.1.4"]

                 ;; DB stuff
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.hsqldb/hsqldb "2.4.1"]
                 
                 ;; Async fun
                 [org.clojure/core.async "0.4.490"]
                 ]
  
  :main zero-one)
