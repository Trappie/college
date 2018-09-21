(define (find s predicate)
    (if (equal? s nil)
        #f
        (if (predicate (car s))
            (car s)
            (find (cdr-stream s) predicate)
        )
    )

)

(define (scale-stream s k)
    (if (equal? s nil)
        nil
        (cons-stream (* k (car s)) (scale-stream (cdr-stream s) k))
    )
)

(define (has-cycle s)

    (define (in s past) ; to see whether s is in past
        (if (equal? past nil)
            #f
            (if (equal? s (car past))
                #t
                (in s (cdr past))
            )
        )
    )

    (define (test-cycle s past)
        (if (equal? s nil)
            #f
            (if (in s past)
                #t
                (test-cycle (cdr-stream s) (cons s past))
            )
        )
    )

    (test-cycle s ())
)
(define (has-cycle-constant s)
  'YOUR-CODE-HERE
)
