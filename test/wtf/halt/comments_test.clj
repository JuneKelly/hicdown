(ns wtf.halt.comments-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))

(t/deftest comments
  (t/testing "Comments"
    (test-parse
     "comments/basic.hd"
     `[:Document
       [:comment ";; A note"]
       [:Block
        ~@(s "Test one ")
        [:Segment [:tag ":a"]
         [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
         ~@(s " foo")]
        ~@(s " bar.")]
       [:comment ";; Another note"]
       [:Block
        ~@(s "Hello.")]])

    (test-parse
     "comments/comment-mark-inside-blocks.hd"
     `[:Document
       [:comment ";; A note"]
       [:Block
        ~@(s "Some text ;; with semi-colons.")]
       [:comment ";; Another note"]
       [:Block
        ~@(s ";; this doesn't count as a comment\n")
        ~@(s "because it's actually inside a block.")]])))
