(ns wtf.halt.hicdown-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [wtf.halt.hicdown :as hicdown]))

;; Helpers
(defn test-file [path]
  (-> (io/resource (str "test/" path))
      (slurp)))

(defn parse-test-file [path]
  (-> (test-file path)
      (hicdown/parser)))

(defn parses [path expected-data]
  (is (= (parse-test-file path)
         expected-data)))

(defn test-parse [path expected-data]
  (testing (str "Parse " path)
    (parses path expected-data)))

(defn s [string]
  (str/split string #""))
;; Tests
(deftest simple-document
  (testing "Simple documents"

    (test-parse
     "simple/one-block.hd"
     `[:Document
       [:Block
        ~@(s "Hello this is a simple")
        [:nl]
        ~@(s "block of text")]])

    (test-parse
     "simple/one-block-with-leading-blank-lines.hd"
     `[:Document
       [:Block
        ~@(s "Hello")]])

    (test-parse
     "simple/three-blocks.hd"
     `[:Document
       [:Block
        ~@(s "Test one.")]
       [:Block
        ~@(s "Two three")
        [:nl]
        ~@(s "four five.")]
       [:Block
        ~@(s "Six seven")
        [:nl]
        ~@(s "eight nine.")]])

    (test-parse
     "simple/blocks-with-segments.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":foo"]
         ~@(s "bar")]
        ~@(s " baz.")]
       [:Block
        ~@(s "Test ")
        [:Segment
         [:tag ":foo"]
         [:Attrs [:KVPair [:key ":a"] [:val "b"]]]
         ~@(s " bar")]
        ~@(s " baz.")]
       [:Block
        ~@(s "Test ")
        [:Segment
         [:tag ":foo"]
         [:Attrs [:KVPair [:key ":a"] [:val "b"]]]]
        ~@(s " baz.")]]))

  (testing "Segments"

    (test-parse
     "segments/empty.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]]
        ~@(s " foo.")]])

    (test-parse
     "segments/empty-with-attrs.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Attrs [:KVPair [:key ":x"] [:val "y"]]]]
        ~@(s " foo.")]])

    (test-parse
     "segments/with-content-text.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         ~@(s "foo")]
        ~@(s " bar.")]])

    (test-parse
     "segments/with-content-text-and-attrs.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
         ~@(s " foo")] ;; Note the extra space here, should be parsed out?
        ~@(s " bar.")]])

    (test-parse
     "segments/spanning-newline.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
         ~@(s " foo")
         [:nl]
         ~@(s "bar baz")]
        ~@(s " quux.")]])

    (test-parse
     "segments/with-escapes.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:esc "\\["]
        ~@(s " something ")
        [:esc "\\]"]
        ~@(s " here.")]
       [:Block
        ~@(s "And ")
        [:esc "\\{"]
        ~@(s " something else ")
        [:esc "\\}"]
        ~@(s " here.")]])

    (test-parse
     "segments/with-escapes-and-segments.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
         ~@(s " foo")]
        ~@(s " bar ")
        [:esc "\\["]
        ~@(s " baz ")
        [:esc "\\]"]
        ~@(s ".")]])

    (test-parse
     "segments/newline-escape.hd"
     `[:Document
       [:Block
        ~@(s "Test one two")
        [:nl]
        [:esc "\\\n"]
        ~@(s "three four.")]])

    (test-parse
     "segments/backslash-escape.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:esc "\\\\"]
        [:Segment [:tag ":a"]
         ~@(s "foo")]
        ~@(s " bar.")]])))
