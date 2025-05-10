package fr.cch.allo_movie.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Gestion de l'exception NOT FOUND
 */
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomException extends RuntimeException{

  /*
     Variables à gérer lors de l'exception
  */
  private final String nomRessource;
  private final String nomChamp;
  private final Object valeurChamp;

  /**
   * Constructeur de l'exception
   * @param nomRessource, la ressource
   * @param nomChamp, le champ
   * @param valeurChamp, la valeur du champ
   */
  public CustomException(String nomRessource, String nomChamp, Object valeurChamp) {
    // "%s" sera remplacé par les valeurs réelles lors de l'exécution
    super(String.format("%s not found with %s : '%s", nomRessource, nomChamp, valeurChamp));
    this.nomRessource = nomRessource;
    this.nomChamp = nomChamp;
    this.valeurChamp = valeurChamp;
  }

}
