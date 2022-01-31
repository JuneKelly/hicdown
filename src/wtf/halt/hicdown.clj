(ns wtf.halt.hicdown
  (:require [instaparse.core :as insta]
            [wtf.halt.hicdown.html :as html]
            [clojure.java.io :as io]))

(def parser
  (insta/parser (io/resource "hicdown.bnf")))

(defn parse-string [text]
  (parser text))

(defn render-string [text]
  (-> text
      (parse-string)
      (html/tree-to-html)))
