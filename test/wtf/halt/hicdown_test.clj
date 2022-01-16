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

;; Tests
(deftest simple-document
  (testing "Simple documents"
    (parses "simple/one-block.hd"
           [:Document
            [:Block [:TextContent
                     "Hello this is a simple"
                     [:nl "\n"]
                     "block of text"]]])
    (parses "simple/one-block-with-leading-blank-lines.hd"
           [:Document
            [:Block [:TextContent
                     "Hello"]]])
    (parses "simple/three-blocks.hd"
           [:Document
            [:Block [:TextContent
                     "Test one."]]
            [:Block [:TextContent
                     "Two three"
                     [:nl "\n"]
                     "four five."]]
            [:Block [:TextContent
                     "Six seven"
                     [:nl "\n"]
                     "eight nine."]]])
    (parses "simple/blocks-with-segments.hd"
            [:Document
             [:Block [:TextContent
                      "Test "
                      [:Segment [:tag ":foo"]
                       [:TextContent "bar"]]
                      " baz."]]
             [:Block [:TextContent
                      "Test "
                      [:Segment
                       [:tag ":foo"]
                       [:Attrs [:KVPair [:key ":a"] [:val "b"]]]
                       [:TextContent " bar"]]
                      " baz."]]
             [:Block [:TextContent
                      "Test "
                      [:Segment
                       [:tag ":foo"]
                       [:Attrs [:KVPair [:key ":a"] [:val "b"]]]]
                      " baz."]]]))

  (testing "Segments"
    (parses "segments/empty.hd"
            [:Document
             [:Block [:TextContent
                      "Test "
                      [:Segment [:tag ":a"]]
                      " foo."]]])
    (parses "segments/empty-with-attrs.hd"
            [:Document
             [:Block [:TextContent
                      "Test "
                      [:Segment [:tag ":a"]
                       [:Attrs [:KVPair [:key ":x"] [:val "y"]]]]
                      " foo."]]])
    (parses "segments/with-content-text.hd"
            [:Document
             [:Block [:TextContent
                      "Test "
                      [:Segment [:tag ":a"]
                       [:TextContent "foo"]]
                      " bar."]]])
    (parses "segments/with-content-text-and-attrs.hd"
            [:Document
             [:Block [:TextContent
                      "Test "
                      [:Segment [:tag ":a"]
                       [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
                       [:TextContent " foo"]] ;; Note the extra space here, should be parsed out?
                      " bar."]]])

    ))
