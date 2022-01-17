(ns wtf.halt.simple-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test :refer [test-parse s]]))

(t/deftest simple-document
  (t/testing "Simple documents"
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
        ~@(s " baz.")]])))
