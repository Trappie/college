; Q1
(define (compose-all funcs)
  (define (fn x)
    (define result ((car funcs) x))
    ((compose-all (cdr funcs)) result)
  )
  (if (equal? funcs nil)
    (lambda (x) x)
    fn
  )
)

; Q2
(define (tail-replicate x n)
    (define (tail curr-list curr-count)
        (if (equal? n curr-count)
            curr-list
            (tail (cons x curr-list) (+ curr-count 1))
        )

    )
    (tail () 0)
)
