<WN>
{
    let $file:=doc('arabic.xml')
    let $synsets:=$file//item[@type="synset"]
    for $synset in $synsets
	return <SYNSET>
	    <ID>{$synset/@itemid/string()}</ID>
	    <POS>{$synset/@POS/string()}</POS>
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