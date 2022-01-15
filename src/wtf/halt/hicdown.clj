(ns wtf.halt.hicdown
  (:require [instaparse.core :as insta]
              [clojure.java.io :as io])
  (:gen-class))

(def parser
  (insta/parser (io/resource "hicdown.bnf")))

(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))
