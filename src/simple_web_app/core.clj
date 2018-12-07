(ns simple-web-app.core
  (:require [aleph.http :as http]
            [ring.middleware
             [params :refer [wrap-params]]
             [keyword-params :refer [wrap-keyword-params]]]
            [compojure.core :as c]
            [simple-db.core :refer [get-album-by-year]]
            [hiccup.core :as html]
            [clojure.string :as string]
            [cheshire.core :as json]))

(defn hello-user-agent [user-agent]
  (html/html
   [:html
    [:div#greeting
     "Hello, "
     [:b (string/upper-case user-agent)]]]))

(defn hello-user-agent-json [user-agent]
  (json/generate-string {}))



(c/defroutes app*
  (c/GET "/greet" [:as request]
         {:status 200
          :headers {:content-type "text/html"}
          :body (hello-user-agent
                 (-> request :headers :user-agent))})

  (c/GET "/greet-json" [:as request]
         {
          :status 200
          :headers {:content-type "application/json"}
          :body (hello-user-agent-json
                 (-> request :headers :user-agent))
         })


  (c/GET "/album/:year" [year]
    {:status 200
     :headers {:content-type "application/json"} 
     :body (json/generate-string (get-album-by-year year))
    })
  )

(def app
  (c/routes
   (-> #'app* ; ??
       wrap-keyword-params
       wrap-params)))

(defonce server (http/start-server
             #'app
             {:port 2022}))

(comment
  (.close server))
