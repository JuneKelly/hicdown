(ns wtf.halt.simple-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))

(t/deftest simple-document
  (t/testing "Simple documents"
    (test-parse
     "simple/one-block.hd"
     `[:Document
       [:Block
        ~@(s "Hello this is a simple\n")
        ~@(s "block of text\n")]])

    (test-parse
     "simple/one-block-with-leading-blank-lines.hd"
     `[:Document
       [:Block "\n"]
       [:Block
        ~@(s "Hello\n")]])

    (test-parse
     "simple/three-blocks.hd"
     `[:Document
       [:Block
        ~@(s "Test one.")]
       [:Block
        ~@(s "Two three\n")
        ~@(s "four five.")]
       [:Block
        ~@(s "Six seven\n")
        ~@(s "eight nine.\n")]])

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
        ~@(s " baz.\n")]])))
