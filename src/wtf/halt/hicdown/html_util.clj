(ns wtf.halt.hicdown.html-util
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as s]))

(defn escape-html-char [ch]
  (match ch
    "&" "&amp;"
    "<" "&lt;"
    ">" "&gt;"
    :else ch))

(defn un-escape [e]
  (subs e 1))

(defn as-chars [xs]
  (map #(if (vector? %1)
          (un-escape (second %1))
          (escape-html-char %1))
       xs))

(def void-tags
  #{"area" "base" "br" "col" "command" "embed" "hr" "img" "input" "keygen" "link"
    "meta" "param" "source" "track" "wbr"})

(defn is-void-tag [s]
  (void-tags s))

(defn html-void-element [name attrs]
  (str "<" name (if attrs
                  (str " " attrs)
                  "")
       ">"))

(defn html-element [name attrs content]
  (if (is-void-tag name)
    (html-void-element name attrs)
    (str "<" name (if attrs
                    (str " " attrs)
                    "")
         ">"
         content
         "</" name ">")))

(declare render-segment render-verbatim)

(defn render-node [node]
  (match node
    [:key s] s
    [:val s] s
    [:Pair k v] (str (render-node k) "=" (render-node v))
    [:Atom s] s
    [:QAtom & r] (apply str (as-chars r))
    [:Qval & r] (str "\"" (apply str (as-chars r)) "\"")
    [:Attrs & r] (s/join " " (map render-node r))
    [:tag s] (subs s 1)
    [:Segment & _] (render-segment node)
    [:Text & r] (apply str (as-chars r))
    [:VerbatimText & r] (render-verbatim r)
    [:Block [:Segment & _]] (str "\n" (render-node (second node)) "\n")
    [:Block & r] (html-element "div" nil (apply str (map render-node r)))
    [:Document [:Attrs & _] & r] (html-element "root"
                                               (render-node (second node))
                                               (apply str (map render-node r)))
    [:Document & r] (html-element "root" nil (apply str (map render-node r)))))

(defn render-verbatim [xs]
  (apply str (->> xs
                  (drop-while #(vector? %1))
                  (take-while #(not (vector? %1)))
                  (map #(escape-html-char %1)))))

(defn render-segment [node]
  (match node
    [:Segment] :empty
    [:Segment
     ([:tag t] :as tag)] (html-element (render-node tag) nil "")
    [:Segment
     ([:tag t] :as tag)
     [:Attrs & _]] (html-element (render-node tag) (render-node (nth node 2)) "")
    [:Segment
     ([:tag t] :as tag)
     [:Attrs & _]
     & r] (html-element (render-node tag) (render-node (nth node 2)) (apply str (map render-node r)))
    [:Segment
     ([:tag t] :as tag)
     & r] (html-element (render-node tag) nil (apply str (map render-node r)))))
