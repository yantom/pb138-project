package pb138.wordnetconvert.backend;

import pb138.wordnetconvert.WordnetException;

/**
 * interface for Wordnet converters
 * @author Jan Tomášek, uco: 422677
 * @author Ondřej Bulla, uco: 422296
 * @version 11.6.2015
 */
public interface WordnetConverter {
    /**
     * This method converts Wordnet at sourcePath to DEBVisDic format and stores it at destinationPath.
     * @param sourcePath
     * @param destinationPath
     * @throws WordnetException throws exception if anything goes wrong, in this case no data will be stored
     */
    void convert(String sourcePath, String destinationPath) throws WordnetException;
}
