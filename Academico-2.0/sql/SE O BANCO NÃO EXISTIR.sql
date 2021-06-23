-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema academico
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema academico
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `academico` DEFAULT CHARACTER SET utf8 ;
USE `academico` ;

-- -----------------------------------------------------
-- Table `academico`.`cursos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academico`.`cursos` ;

CREATE TABLE IF NOT EXISTS `academico`.`cursos` (
  `codigo` INT NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Tabela de cursos';


-- -----------------------------------------------------
-- Table `academico`.`alunos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academico`.`alunos` ;

CREATE TABLE IF NOT EXISTS `academico`.`alunos` (
  `matricula` BIGINT NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  `fone` VARCHAR(11) NULL DEFAULT NULL,
  `endereco` VARCHAR(60) NULL DEFAULT NULL,
  `cep` VARCHAR(7) NULL DEFAULT NULL,
  `sexo` CHAR(1) NOT NULL,
  `curso` INT(3) NOT NULL,
  PRIMARY KEY (`matricula`),
  INDEX `fk_Aluno_curso1` (`curso` ASC),
  CONSTRAINT `fk_Aluno_curso1`
    FOREIGN KEY (`curso`)
    REFERENCES `academico`.`cursos` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Tabela de alunos';


-- -----------------------------------------------------
-- Table `academico`.`centros`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academico`.`centros` ;

CREATE TABLE IF NOT EXISTS `academico`.`centros` (
  `sigla` VARCHAR(5) NOT NULL,
  `nome` VARCHAR(60) NULL DEFAULT NULL,
  PRIMARY KEY (`sigla`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Tabela de centros';


-- -----------------------------------------------------
-- Table `academico`.`disciplinas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academico`.`disciplinas` ;

CREATE TABLE IF NOT EXISTS `academico`.`disciplinas` (
  `codigo` VARCHAR(8) NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `ch` INT(3) NOT NULL DEFAULT '60',
  `centro` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`codigo`),
  INDEX `fk_disciplinas_centros1_idx` (`centro` ASC),
  CONSTRAINT `fk_disciplinas_centros1`
    FOREIGN KEY (`centro`)
    REFERENCES `academico`.`centros` (`sigla`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Tabela de disciplinas';


-- -----------------------------------------------------
-- Table `academico`.`professores`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academico`.`professores` ;

CREATE TABLE IF NOT EXISTS `academico`.`professores` (
  `matricula` BIGINT NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  `rg` BIGINT(11) NOT NULL,
  `cpf` BIGINT(11) NOT NULL,
  `endereco` VARCHAR(60) NULL DEFAULT NULL,
  `fone` VARCHAR(11) NULL DEFAULT NULL,
  `centro` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`matricula`),
  INDEX `fk_professor_centro` (`centro` ASC),
  CONSTRAINT `fk_professor_centro`
    FOREIGN KEY (`centro`)
    REFERENCES `academico`.`centros` (`sigla`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Tabela de professores';


-- -----------------------------------------------------
-- Table `academico`.`curriculos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academico`.`curriculos` ;

CREATE TABLE IF NOT EXISTS `academico`.`curriculos` (
  `codigo` BIGINT NOT NULL,
  `curso` INT NOT NULL,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codigo`),
  INDEX `fk_curriculos_cursos1_idx` (`curso` ASC),
  CONSTRAINT `fk_curriculos_cursos1`
    FOREIGN KEY (`curso`)
    REFERENCES `academico`.`cursos` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `academico`.`grades`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academico`.`grades` ;

CREATE TABLE IF NOT EXISTS `academico`.`grades` (
  `codigo` BIGINT NOT NULL,
  `curriculo` BIGINT NOT NULL,
  `disciplina` VARCHAR(8) NOT NULL,
  `periodo` INT(2) NOT NULL,
  INDEX `fk_estrutura_curricular_has_disciplinas_disciplinas1_idx` (`disciplina` ASC),
  INDEX `fk_estrutura_curricular_has_disciplinas_estrutura_curricula_idx` (`curriculo` ASC),
  PRIMARY KEY (`codigo`),
  CONSTRAINT `fk_estrutura_curricular_has_disciplinas_estrutura_curricular1`
    FOREIGN KEY (`curriculo`)
    REFERENCES `academico`.`curriculos` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_estrutura_curricular_has_disciplinas_disciplinas1`
    FOREIGN KEY (`disciplina`)
    REFERENCES `academico`.`disciplinas` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `academico`.`turmas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academico`.`turmas` ;

CREATE TABLE IF NOT EXISTS `academico`.`turmas` (
  `codigo` BIGINT NOT NULL,
  `grade` BIGINT NOT NULL,
  `ano` INT(4) NOT NULL,
  `semestre` INT(2) NOT NULL,
  `professor` BIGINT NOT NULL,
  `vagas` INT(3) NOT NULL,
  INDEX `fk_turmas_professores1_idx` (`professor` ASC),
  PRIMARY KEY (`codigo`),
  INDEX `fk_turmas_grades1_idx` (`grade` ASC),
  CONSTRAINT `fk_turmas_professores1`
    FOREIGN KEY (`professor`)
    REFERENCES `academico`.`professores` (`matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_turmas_grades1`
    FOREIGN KEY (`grade`)
    REFERENCES `academico`.`grades` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `academico`.`matriculas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `academico`.`matriculas` ;

CREATE TABLE IF NOT EXISTS `academico`.`matriculas` (
  `aluno` BIGINT NOT NULL,
  `turma` BIGINT NOT NULL,
  `frequencia` INT(3) NULL DEFAULT NULL,
  `n1` DECIMAL(3,2) NULL DEFAULT NULL,
  `n2` DECIMAL(3,2) NULL DEFAULT NULL,
  `ef` DECIMAL(3,2) NULL DEFAULT NULL,
  PRIMARY KEY (`aluno`, `turma`),
  INDEX `fk_matriculas_turmas1_idx` (`turma` ASC),
  INDEX `fk_matriculas_alunos1_idx` (`aluno` ASC),
  CONSTRAINT `fk_matriculas_turmas1`
    FOREIGN KEY (`turma`)
    REFERENCES `academico`.`turmas` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_matriculas_alunos1`
    FOREIGN KEY (`aluno`)
    REFERENCES `academico`.`alunos` (`matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
