(ns trains.core
  (:require [clojure.set :use union])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

; city-seq in the format of keywords of cities
; distances is a map of distances by dest and origin; ex: {:a {:b 2 :c 3}}
; returns the distance of the whole path or nil if such path is not possible
(defn calculate-distance [city-seq distances]
  (let [routes (partition 2 1 city-seq)]
    (try
      (reduce #(+ %1 (get-in distances %2))
              0
              routes)
      (catch NullPointerException e nil))))


; start city
; end city
; maximum stops
; distances is the same as before
(defn find-trips [start end max distances]
  ; acc = stops s = start, e = end, m = max; d = distances
  (letfn [(find-trips-acc [acc s e m d]
              (apply union (filter #(seq %)
                                   (cons (when (= s e)
                                               #{(conj acc e)})
                                         (when (not= m 0)
                                               (map #(find-trips-acc (conj acc s) % e (dec m) d)
                                                    (keys (s d))))))))]
    (find-trips-acc [] start end max distances)))


; same as above but with exactly stops
(defn find-trips-exactly [start end max distances]
  ; acc = stops s = start, e = end, m = max; d = distances
  (letfn [(find-trips-acc [acc s e m d]
             (if (= m 0)
                 (when (= s e)
                       #{(conj acc e)})
                 (apply union
                        (map #(find-trips-acc (conj acc s) % e (dec m) d)
                             (keys (s d))))))]
    (find-trips-acc [] start end max distances)))


; returns the length of the shortest route
(defn shortest-route [start end distances]
  ; len: subtotal of length, s = start, e = end, d = distances
  (letfn [(shortest-route-acc [len s e d]
            (when-let [next-cities (keys (s d))]
              (let [subresults (remove nil?
                                      (map #(when-let [inc-len (get-in d [s %])]
                                                     (if (= % e)
                                                         (+ len inc-len)
                                                         (shortest-route-acc (+ len inc-len) % e (dissoc d s))))
                                           next-cities))]
                (when (seq subresults)
                   (apply min subresults)))))]
    (shortest-route-acc 0 start end distances)))

; returns the routes with less than or equal max-distance
(defn find-routes [start end max-distance distances]
  ; acc = stops s = start, e = end, m = max; d = distances
  (letfn [(find-routes-acc [acc s e m d]
              (if (pos? m)
                 (apply union (filter #(seq %)
                                       (cons (when (= s e)
                                                   #{(conj acc e)})
                                             (map #(find-routes-acc (conj acc s) % e (- m (get-in d [s %])) d)
                                                   (keys (s d))))))
                 #{}))]
    (find-routes-acc [] start end max-distance distances)))
