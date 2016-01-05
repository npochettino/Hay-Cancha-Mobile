END=360;
name=0;
for ((i=0;i<=END;i+= 6)); do
    convert ball.png -background 'rgba(0,0,0,0)' -rotate $i ball_$name.png;
    (( name = name + 1 ));
done