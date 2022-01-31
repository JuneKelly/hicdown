(ns wtf.halt.hicdown
  (:require [instaparse.core :as insta]
            [clojure.java.io :as io]
            [clojure.pprint :as pp])
  (:gen-class))

(def parser
  (insta/parser (io/resource "hicdown.bnf")))

(defn transform-text [& chars]
  [:Text (apply str chars)])

(defn transform [t]
  (insta/transform {:Text transform-text} t))

(defn parse [text]
  (transform (parser text)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (-> (parser (first args))
      (pp/pprint)))
