(ns concurrent-processes.clj
  (:require [clojure.core.async :as async
             :refer [go go-loop
                     chan
                     buffer sliding-buffer dropping-buffer
                     <! <!!
                     >! >!!]]))
