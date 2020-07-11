(ns trains.core-test
  (:require [clojure.test :refer :all]
            [trains.core :refer :all]))

(deftest testing-app
  (testing "calculate-distance"
    (is (= 2 (calculate-distance [:a :b] {:a {:b 2}})))
    (is (= nil (calculate-distance [:a :c] {:a {:b 2}})))
    (is (= 101 (calculate-distance [:a :c :b] {:a {:b 2 :c 1} :b {:a 10 :c 20} :c {:a 200 :b 100}}))))
 (testing "max stops"
    (is (= #{[:a :b]} (find-trips :a :b 1 {:a {:b 4 :c 7}})))
    (is (= #{[:a :c]} (find-trips :a :c 1 {:a {:b 4 :c 7} :b {:c 8}})))
    (is (= #{[:a :b :d] [:a :b :d :b :d] [:a :b :c :d] [:a :c :a :b :d] [:a :c :d] [:a :c :d :b :d] [:a :c :a :c :d]} (find-trips :a :d 4 {:a {:b 4 :c 7} :b {:c 8 :d 2} :c {:a 9 :d 2} :d {:b 4}})))))
