(ns data-modeling
  (:require [clojure.string :as string])
  (:import [java.time Instant]))

(defrecord Person [first-name last-name])

(ancestors Person)
;; Long list!

(def ravindra (->Person "Ravindra" "Jaju"))

(def dhaval (map->Person {:first-name "Dhaval"
                          :last-name "Dalal"}))

(def CONDUCTORS [ravindra dhaval])

{:host "gyaanweb.com"
 :protocol :https
 :port 443
 :path "/clojure-workshop"
 :query-params {:topic "data-modeling"}}

; Records *are* Java classes.
(.first_name dhaval)

(:first-name ravindra)

(defn first-name [person]
  (:first-name person))

(defn last-name [person]
  (:last-name person))

(defn printable-name [{:keys [first-name last-name]}]
  (str last-name ", " first-name))

(printable-name ravindra)
