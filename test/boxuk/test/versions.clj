
(ns boxuk.test.versions
  (:use [boxuk.versions])
  (:use [clojure.test]))

(deftest test-later-version?
    (is (later-version? "1.0.0" "1.0.1"))
    (is (later-version? "1.0.0" "2.0.1"))
    (is (later-version? "1.1.0" "1.2.1"))
    (is (not (later-version? "1.0.1" "1.0.0")))
    (is (not (later-version? "2.0.1" "2.0.0")))
    (is (not (later-version? "3.1" "2.3.4")))
    (is (not (later-version? "1.2.1" "1.1.0"))))

(deftest test-earlier-version?
    (is (earlier-version? "1.0.0" "0.0.9"))
    (is (not (earlier-version? "1.0.0" "1.2.0"))))

(deftest test-version-number-of-varying-lengths
    (is (later-version? "1.0.0" "1.1"))
    (is (later-version? "1.0" "1.0.1"))
    (is (earlier-version? "1.0" "0.0.9")))

(deftest test-same-version
    (is (same-version? "1.0" "1.0"))
    (is (same-version? "1.0" "1.0.0.0.0.0"))
    (is (not (same-version? "1.0" "1.2"))))

(deftest test-latest-version
    (is (= "3.1" (latest-version ["1.2.3" "3.1" "2.3" "1.3.1"])))
    (is (= "3.1" (latest-version ["1.2.3" "1.1" "2.3" "3.1"]))))

(deftest test-earliest-version
    (is (= "1.2.3" (earliest-version ["1.2.3" "3.1" "2.3" "1.3.1"])))
    (is (= "1.1" (earliest-version ["1.2.3" "1.1" "2.3" "3.1"]))))

