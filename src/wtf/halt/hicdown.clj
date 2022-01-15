(ns wtf.halt.hicdown
  (:require [instaparse.core :as insta]
            [clojure.java.io :as io]
            [clojure.pprint :as pp])
  (:gen-class))

(def parser
  (insta/parser (io/resource "hicdown.bnf")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (-> (parser (first args))
      (pp/pprint)))
