(ns wtf.halt.attrs-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))

(t/deftest attrs
  (t/testing "Attrs"

    (test-parse
     "attrs/basic.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":aa"]
         [:Attrs
          [:Pair [:key "bb"] [:val "cc"]]
          [:Pair [:key "dd"] [:val "ee"]]
          [:Atom "gg"]
          [:Atom "hh"]]
         [:Text ~@(s "foo")]]
        [:Text ~@(s " bar.")]]])

    (test-parse
     "attrs/document-level-attrs.hd"
     `[:Document
       [:Attrs
        [:Pair [:key "author"] [:Qval ~@(s "Alice")]]
        [:Pair [:key "date"] [:Qval ~@(s "2022-01-01")]]
        [:Atom "wat"]]
       [:Block
        [:Text ~@(s "Test one.")]]
       [:Block
        [:Text ~@(s "two three.")]]])

    (test-parse
     "attrs/with-commas.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":foo"]
         [:Attrs
          [:Pair [:key "aa"] [:val "bb"]]
          [:Pair [:key "cc"] [:val "dd"]]
          [:Atom "ee"]
          [:Atom "ff"]
          [:Pair [:key "gg"] [:val "hh"]]]
         [:Text ~@(s "bar")]]
        [:Text ~@(s ".")]]])

    (test-parse
     "attrs/with-quoted-atom.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":foo"]
         [:Attrs
          [:QAtom ~@(s "aa")]
          [:Atom "bb"]
          [:Pair [:key "cc"] [:val "dd"]]]
         [:Text ~@(s "bar")]]
        [:Text ~@(s " baz.")]]])

    (test-parse
     "attrs/with-quoted-values.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":aa"]
         [:Attrs
          [:Pair [:key "bb"] [:Qval ~@(s "c  c,")]]
          [:Pair [:key "dd"] [:Qval ~@(s "e}e")]]]
         [:Text ~@(s "foo")]]
        [:Text ~@(s " bar.")]]])

    (test-parse
     "attrs/with-quoted-values-and-escapes.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":aa"]
         [:Attrs
          [:Pair [:key "bb"] [:Qval ~@(s "c  c ") [:valesc "\\\""] ~@(s " z z")]]
          [:Pair [:key "dd"] [:Qval ~@(s "e}e")]]]
         [:Text ~@(s "foo")]]
        [:Text ~@(s " bar.")]]])))
