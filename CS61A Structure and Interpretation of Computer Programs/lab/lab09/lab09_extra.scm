;; Extra Scheme Questions ;;


; Q5
(define lst
  'YOUR-CODE-HERE
)

; Q6
(define (composed f g)
    (define (new_function num)
        (f (g num)) 
    )
    new_function
)

; Q7
(define (remove item lst)
    (filter (lambda (x) (not (= x item))) lst)
)


;;; Tests
(remove 3 nil)
; expect ()
(remove 3 '(1 3 5))
; expect (1 5)
(remove 5 '(5 3 5 5 1 4 5 4))
; expect (3 1 4 4)

; Q8
(define (max a b) (if (> a b) a b))
(define (min a b) (if (> a b) b a))
(define (gcd a b)
    (define (bigger_first_gcd bigger smaller)
        (define remain (modulo bigger smaller))
        (if (= 0 remain)
            smaller 
            (bigger_first_gcd smaller remain)
        ) 
    )
    ; test whether a or b is 0 before calculation
    (if (= a 0)
        b
        (if (= b 0)
            a
            (bigger_first_gcd (max a b) (min a b))
        )
    )
)

;;; Tests
(gcd 24 60)
; expect 12
(gcd 1071 462)
; expect 21

; Q9
(define (no-repeats s)
    (define (is_in item lst)
        (if (null? lst)
            #f
            (if (= item (car lst))
                #t
                (is_in item (cdr lst))
            )
        )
    )
    (define (add_each_recur old new)
        (if (not (null? old))
            (if (is_in (car old) new)
                ; if the first item is in the new list
                (add_each_recur (cdr old) new)
                (add_each_recur (cdr old) (append new (cons (car old) nil)))
            )
            new
        )
    )
    (add_each_recur s ())

)

; Q10
(define (substitute s old new)
    (if (null? s)
        ()
        (if (pair? (car s))
            (cons (substitute (car s) old new) (substitute (cdr s) old new))
            (if (equal? old (car s))
                (cons new (substitute (cdr s) old new))
                (cons (car s) (substitute (cdr s) old new))
            )
        )
    )
)

; Q11
(define (sub-all s olds news)
    (if (null? olds)
        s
        (sub-all (substitute s (car olds) (car news)) (cdr olds) (cdr news))
    )
)
