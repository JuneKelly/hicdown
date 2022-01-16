(ns wtf.halt.hicdown-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
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

;; Tests
(deftest simple-document
  (testing "Simple documents"

    (test-parse "simple/one-block.hd"
           [:Document
            [:Block
             "Hello this is a simple"
             [:nl]
             "block of text"]])

    (test-parse "simple/one-block-with-leading-blank-lines.hd"
           [:Document
            [:Block
             "Hello"]])

    (test-parse "simple/three-blocks.hd"
           [:Document
            [:Block
             "Test one."]
            [:Block
             "Two three"
             [:nl]
             "four five."]
            [:Block
             "Six seven"
             [:nl]
             "eight nine."]])

    (test-parse "simple/blocks-with-segments.hd"
            [:Document
             [:Block
              "Test "
              [:Segment [:tag ":foo"]
               "bar"]
              " baz."]
             [:Block
              "Test "
              [:Segment
               [:tag ":foo"]
               [:Attrs [:KVPair [:key ":a"] [:val "b"]]]
               " bar"]
              " baz."]
             [:Block
              "Test "
              [:Segment
               [:tag ":foo"]
               [:Attrs [:KVPair [:key ":a"] [:val "b"]]]]
              " baz."]]))

  (testing "Segments"

    (test-parse "segments/empty.hd"
            [:Document
             [:Block
              "Test "
              [:Segment [:tag ":a"]]
              " foo."]])

    (test-parse "segments/empty-with-attrs.hd"
            [:Document
             [:Block
              "Test "
              [:Segment [:tag ":a"]
               [:Attrs [:KVPair [:key ":x"] [:val "y"]]]]
              " foo."]])

    (test-parse "segments/with-content-text.hd"
            [:Document
             [:Block
              "Test "
              [:Segment [:tag ":a"]
               "foo"]
              " bar."]])

    (test-parse "segments/with-content-text-and-attrs.hd"
            [:Document
             [:Block
              "Test "
              [:Segment [:tag ":a"]
               [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
               " foo"] ;; Note the extra space here, should be parsed out?
              " bar."]])

    (test-parse "segments/spanning-newline.hd"
                [:Document
                 [:Block
                  "Test "
                  [:Segment [:tag ":a"]
                   [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
                   " foo"
                   [:nl]
                   "bar baz"]
                  " quux."]])
    ))
