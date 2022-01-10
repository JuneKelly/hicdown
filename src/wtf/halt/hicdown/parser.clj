(ns wtf.halt.hicdown.parser
  (:require [instaparse.core :as insta]
            [clojure.java.io :as io]))

(def parser
  (insta/parser (io/resource "hicdown.bnf")))
