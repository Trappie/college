(define (accumulate combiner start n term)
  (define (accumulate_range result index)
      (define result (combiner result (term index)))
      (cond
        ((= index n) result)
        (else (accumulate_range result (+ index 1)))
      )
  )
    ; accumualate from index to n
    ; index should <= n
  (if (= n 0)
    start
    (accumulate_range start 1)
  )
)

(define (accumulate-tail combiner start n term)
  (define (accumulate_range result index)
      (define result (combiner result (term index)))
      (cond
        ((= index n) result)
        (else (accumulate_range result (+ index 1)))
      )
  )
    ; accumualate from index to n
    ; index should <= n
  (if (= n 0)
    start
    (accumulate_range start 1)
  )
)

(define-macro (list-of expr for var in seq if filter-fn)
    (define map-fn (list 'lambda (list var) expr))
    (define filter-fn (list 'lambda (list var) filter-fn))
    (list 'map map-fn (list 'filter filter-fn seq))
)
