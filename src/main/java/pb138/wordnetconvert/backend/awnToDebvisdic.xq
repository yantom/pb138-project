<WN>
{
    let $file:=doc('in')
    let $synsets:=$file//item[@type="synset"]
    for $synset in $synsets
	return <SYNSET>
	    <ID>{$synset/@itemid/string()}</ID>
	    <POS>{if($synset/@POS="s")
			then "a"
			else if($synset/@POS="r")
			     then "b"
			     else $synset/@POS/string()}
	    </POS>
	    <SYNONYM>
		{
		    let $words:=$file//word[@synsetid=$synset/@itemid]
		    for $word in $words
			return <LITERAL>{$word/@value/string()}</LITERAL>
		}
	    </SYNONYM>
	</SYNSET>
}
</WN>