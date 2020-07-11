(ns trains.core-test
  (:require [clojure.test :refer :all]
            [trains.core :refer :all]))

(deftest a-test
  (testing "calculate-distance"
    (is (= 2 (calculate-distance [:a :b] {:a {:b 2}})))
    (is (= nil (calculate-distance [:a :c] {:a {:b 2}})))
    (is (= 101 (calculate-distance [:a :c :b] {:a {:b 2 :c 1} :b {:a 10 :c 20} :c {:a 200 :b 100}})))))
