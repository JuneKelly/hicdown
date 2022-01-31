(ns wtf.halt.simple-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))

(t/deftest simple-document
  (t/testing "Simple documents"
    (test-parse
     "simple/one-block.hd"
     `[:Document
       [:Block
        [:Text
         "Hello this is a simple\nblock of text"]]])

    (test-parse
     "simple/one-block-with-leading-blank-lines.hd"
     `[:Document
       [:Block
        [:Text "Hello"]]])

    (test-parse
     "simple/three-blocks.hd"
     `[:Document
       [:Block
        [:Text "Test one."]]
       [:Block
        [:Text "Two three\nfour five."]]
       [:Block
        [:Text "Six seven\neight nine."]]])

    (test-parse
     "simple/blocks-with-segments.hd"
     `[:Document
       [:Block
        [:Text "Test "]
        [:Segment [:tag ":foo"]
         [:Text "bar"]]
        [:Text " baz."]]
       [:Block
        [:Text "Test "]
        [:Segment
         [:tag ":foo"]
         [:Attrs [:Pair [:key "a"] [:val "b"]]]
         [:Text "bar"]]
        [:Text " baz."]]
       [:Block
        [:Text "Test "]
        [:Segment
         [:tag ":foo"]
         [:Attrs [:Pair [:key "a"] [:val "b"]]]]
        [:Text " baz."]]])))
