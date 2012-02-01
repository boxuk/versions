
(ns boxuk.versions
    (:require [clojure.string :as string]))

(defn- split-versions
    "Split a version string into parts"
    [version]
    (if (nil? version)
        '[0 0 0]
        (string/split version #"\.")))

(defn- version-pairs
    "Breaks 2 version number strings into interleaved pairs"
    [current-version next-version]
    (map
        #(list (Integer/parseInt %1) (Integer/parseInt %2))
        (split-versions current-version)
        (split-versions next-version)
    ))

;; Public

(defn later-version?
    "Indicates if next-version is a greater version than current-version"
    [current-version next-version]
    (let [pairs (version-pairs current-version next-version)]
        (loop [[x y] (first pairs) more (rest pairs)]
            (cond
                (or (nil? x) (nil? y)) false
                (> y x) true
                (< y x) false
                :default (recur (first more) (rest more))))))

(defn earlier-version?
    "Indicates if prev-version is less then the current-version"
    [current-version prev-version]
    (not (later-version? current-version
                         prev-version)))

