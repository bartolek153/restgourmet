package app.restgourmet.api.usermanagement.service.impl;

import org.springframework.stereotype.Service;

import app.restgourmet.api.usermanagement.service.spec.NicknameGenerator;

import java.util.List;
import java.util.Random;

@Service
public class NicknameGeneratorImpl implements NicknameGenerator {

  private final List<String> adjectives = List.of(
      "brave", "clever", "cunning", "fierce", "gentle",
      "loyal", "mighty", "noble", "playful", "quick", "wise");

  private final List<String> animals = List.of(
      "lion", "tiger", "eagle", "wolf", "fox", "bear",
      "hawk", "leopard", "panther", "falcon", "otter");

  private final Random random = new Random();

  public String generateNickname() {
    String adjective = adjectives.get(random.nextInt(adjectives.size()));
    String animal = animals.get(random.nextInt(animals.size()));
    int number = 1000 + random.nextInt(9000); // 4-digit number

    return adjective + "_" + animal + "_" + number;
  }
}
