(ns wtf.halt.hicdown.main
  (:require [wtf.halt.hicdown :as hicdown]
            [clojure.string :as s]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.pprint :as pp])
  (:gen-class))

(defn read-stdin []
  (s/join "\n" (line-seq (java.io.BufferedReader. *in*))))

(defn do-parse []
  (-> (read-stdin)
      (hicdown/parse-string)
      (pp/pprint)))

(defn do-render []
  (-> (read-stdin)
      (hicdown/render-string)
      (print)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (s/join \newline errors)))

(def cli-options
  [["-h" "--help" "Help"]])

(defn usage [options-summary]
  (->> ["hicdown action [options]"
        ""
        "Actions: "
        "  - parse: Reads from stdin, prints parsed tree"
        "  - render: Reads from stdin, prints html"
        ""
        "Options:"
        options-summary]
       (s/join \newline)))

(defn validate-args [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      ;; Exit with help message
      (:help options)
      {:exit-message (usage summary) :ok? true}
      ;; Errors?
      errors
      {:exit-message (error-msg errors)}
      ;; Actions
      (and (= 1 (count arguments))
           (#{"parse" "render"} (first arguments)))
      {:action (first arguments) :options options}
      ;; failed custom validation => exit with usage summary
      :else
      {:exit-message (usage summary)})))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{:keys [action exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (case action
        "parse" (do-parse)
        "render" (do-render)))))
