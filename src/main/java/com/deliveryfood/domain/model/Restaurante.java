package com.deliveryfood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.deliveryfood.core.validation.Groups;
import com.deliveryfood.core.validation.ValorZeroIncluiDescricao;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank
    @Column(nullable = false)
    private String nome;

    // @NotNull
    // @PositiveOrZero
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    // @Valid
    // @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    // @NotNull
    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name="cozinha_id", nullable = false) // o "name" por padrão é <nome_variavel>_id, ou seja, ficaria cozinha_id. Nesse caso, então poderia deixar só @JoinColumn
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();
}
