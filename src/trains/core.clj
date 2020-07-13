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
  ; acc = stops s = start, m = max
  (letfn [(find-trips-acc [acc s m]
              (apply union (filter #(seq %)
                                   (cons (when (= s end)
                                               #{(conj acc end)})
                                         (when (not= m 0)
                                               (map #(find-trips-acc (conj acc s) % (dec m))
                                                    (keys (s distances))))))))]
    (set (remove #(= 1 (count %))
                 (find-trips-acc [] start max)))))


; same as above but with exactly stops
(defn find-trips-exactly [start end max distances]
  ; acc = stops s = start, m = max
  (letfn [(find-trips-acc [acc s m]
             (if (= m 0)
                 (when (= s end)
                       #{(conj acc end)})
                 (apply union
                        (map #(find-trips-acc (conj acc s) % (dec m))
                             (keys (s distances))))))]
    (find-trips-acc [] start max)))


; returns the length of the shortest route
(defn shortest-route [start end distances]
  ; len: subtotal of length, s = start, d = distances
  (letfn [(shortest-route-acc [len s d]
            (when-let [next-cities (keys (s d))]
              (let [subresults (remove nil?
                                      (map #(when-let [inc-len (get-in d [s %])]
                                                     (if (= % end)
                                                         (+ len inc-len)
                                                         (shortest-route-acc (+ len inc-len) % (dissoc d s))))
                                           next-cities))]
                (when (seq subresults)
                   (apply min subresults)))))]
    (shortest-route-acc 0 start distances)))


; returns the routes with less than max-distance
(defn find-routes [start end max-distance distances]
  ; acc = stops s = start, m = max
  (letfn [(find-routes-acc [acc s m]
              (if (pos? m)
                 (apply union (filter #(seq %)
                                       (cons (when (= s end)
                                                   #{(conj acc end)})
                                             (map #(find-routes-acc (conj acc s) % (- m (get-in distances [s %])))
                                                   (keys (s distances))))))
                 #{}))]
    (set (remove #(= 1 (count %))
                 (find-routes-acc [] start max-distance)))))
