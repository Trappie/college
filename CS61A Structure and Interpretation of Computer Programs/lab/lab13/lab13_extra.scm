; Q4
;(define (rle s)
;
;    ; s is a stream
;
;    ;; input is the current input stream, result is the current output stream, curr is the current tuple of the run
;    (define (modify stream-result latest-result)
;        
;    )
;    (define (run-stream input result curr)
;        (if (equal? input)
;            result
;            (if (equal? (car input) (car curr))
;                (run-stream (cdr-stream input) result (cons (car curr) (car (cdr curr))))
;                (run-stream (cdr-stream input) (modify result curr) (list (car input) 1))
;                
;            )
;        )
;    )
;
;    (if (equal? s nil)
;        ()
;        (run-stream s () (list (car s) 0))
;    )
;    ; the return value should be a stream too
;
;)

(define (rle s)
    (define (increase pair)
        ; assert (not (equal? pair ()))
        (define count (car (cdr pair)))
        (define count (+ 1 count))
        (define latter (cons count nil))
        (cons (car pair) latter)
    )

    (define (append-stream stream item)
        (if (equal? stream ())
            (cons-stream item nil)
            (cons-stream (car stream) (append-stream (cdr-stream stream) item))
        )
    )
    ;; INPUT is the input stream, OUTPUT is the output stream, CURR is the current repeated chars
    (define (run-stream input output curr) 
        (if (equal? input ())
            (append-stream output curr)
            ; if the input has nothing to add, flush the curr to the current stream and the current stream would be the final answer
            (if (equal? (car curr) (car input)) ; if repeat
                (run-stream (cdr-stream input) output (increase curr)) ; if repeat
                (run-stream (cdr-stream input) (append-stream output curr) (cons (car input) (cons 1 nil)))
            )
        )
    )


    (if (equal? s nil)
        ()
        (run-stream s () (cons (car s) (cons 0 nil))) ; the last () should be the curr-stream
    )
)

; Q4 testing functions
(define (list-to-stream lst)
    (if (null? lst) nil
                    (cons-stream (car lst) (list-to-stream (cdr lst))))
)

(define (stream-to-list s)
    (if (null? s) nil
                 (cons (car s) (stream-to-list (cdr-stream s))))
)

; Q5
(define (insert n s)
    (define (insert left right)
        (if (equal? right ())
            (append left (cons n nil))
            (if (<= n (car right))
                (append left (cons n right))        
                (insert (append left (cons (car right) nil)) (cdr right))
            )
        )
    )
    (insert () s)
)

; Q6
(define (deep-map fn s)
    (if (equal? s nil)
        nil
        (if (list? (car s))
            (cons (deep-map fn (car s)) (deep-map fn (cdr s)))
            (cons (fn (car s)) (deep-map fn (cdr s)))
        )
    )
)

; Q7
; Feel free to use these helper procedures in your solution
(define (map fn s)
  (if (null? s) nil
      (cons (fn (car s))
            (map fn (cdr s)))))

(define (filter fn s)
  (cond ((null? s) nil)
        ((fn (car s)) (cons (car s)
                            (filter fn (cdr s))))
        (else (filter fn (cdr s)))))

; Implementing and using these helper procedures is optional. You are allowed
; to delete them.
(define (unique s)
  'YOUR-CODE-HERE
  nil
)

(define (count name s)
  'YOUR-CODE-HERE
  nil
)

(define (add name lst)
    (if (equal? lst nil)
        (cons (cons name 1) nil)
        (if (equal? name (car (car lst)))
            ; if the name equal to the first one in the list
            (cons (cons name (+ 1 (cdr (car lst)))) (cdr lst))
            (cons (car lst) (add name (cdr lst)))
        )
    )    
)

(define (tally names)
    (define (add-recur input output)
        (if (equal? input nil)
            output
            (add-recur (cdr input) (add (car input) output))
        )
    )
    (add-recur names ())
)
